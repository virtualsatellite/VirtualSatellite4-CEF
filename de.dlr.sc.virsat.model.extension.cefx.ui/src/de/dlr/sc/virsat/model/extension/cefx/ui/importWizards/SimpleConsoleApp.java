/*
 * SimpleConsoleApp.java
 *
 * Copyright (c) 2015-2020 RHEA System S.A.
 */

package de.dlr.sc.virsat.model.extension.cefx.ui.importWizards;

import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.EngineeringModel;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.PossibleFiniteState;
import cdp4common.engineeringmodeldata.PossibleFiniteStateList;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EmailAddress;
import cdp4common.sitedirectorydata.ParameterType;
import cdp4common.sitedirectorydata.Person;
import cdp4common.sitedirectorydata.SiteDirectory;
import cdp4common.sitedirectorydata.VcardEmailAddressKind;
import cdp4common.types.OrderedItem;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4dal.exceptions.TransactionException;
import cdp4dal.operations.ThingTransactionImpl;
import cdp4dal.operations.TransactionContextResolver;
import cdp4servicesdal.CdpServicesDal;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;

public class SimpleConsoleApp {

	// Commands
	private final static String OPEN = "open";
	private final static String REFRESH = "refresh";
	private final static String RELOAD = "reload";
	private final static String CLOSE = "close";
	private final static String RESTORE = "restore";
	private final static String GET_ITERATION = "get_iteration";
	private final static String POST_PERSON = "post_person";
	private final static String POST_PARAMETER = "post_parameter";
	private final static String POST_PFSL = "post_pfsl";
	private final static String POST_PFSL_REORDER = "post_pfsl_reorder";
	private final static String REMOVE_PARAMETER = "remove_parameter";

	/**
	 * The {@link Credentials} that are used to connect the data-store
	 */
	private Credentials credentials;

	/**
	 * A flag signalling whether this app is still running
	 */
	private boolean isRunning = true;

	/**
	 * The {@link Session} that is used to communicate with the data-store
	 */
	private Session session;

	/**
	 * The {@link URI} of the data-store to connect to
	 */
	private URI uri;

	/**
	 * Runs this console app
	 */
	public void run() {
		Scanner in = new Scanner(System.in);

		this.printSeparator();
		System.out.println("Welcome to CDP4 SDKJ sample app!");
		this.printSeparator();

		System.out.println("Enter your user name (default is admin, just press Enter):");
		String userName = in.nextLine().isEmpty() ? "admin" : in.nextLine();

		System.out.println("Enter your password (to use default just press Enter):");
		String pass = in.nextLine().isEmpty() ? "pass" : in.nextLine();

		System.out.println(
				"Enter a server's URL for future requests (default is https://cdp4services-test.cdp4.org, just press Enter):");
		this.uri = URI.create(in.nextLine().isEmpty() ? "https://cdp4services-test.cdp4.org" : in.nextLine());

		var dal = new CdpServicesDal();
		this.credentials = new Credentials(userName, pass, this.uri, null);
		this.session = new SessionImpl(dal, this.credentials);

		this.printCommands();

		while (this.isRunning) {
			try {
				this.executeCommand(in.nextLine());
			} catch (Exception ex) {
				this.printSeparator();
				System.out.println("Something went wrong. Sorry about that.");
				System.out.println(ex.getMessage());
				this.printSeparator();
			}
		}

		in.close();
	}

	/**
	 * Executes a supplied command
	 *
	 * @param command Name of a command to execute
	 */
	private void executeCommand(String command)
			throws TransactionException, InterruptedException, ExecutionException, URISyntaxException, IOException {
		String[] args = command.strip().split(" ");
		switch (args[0]) {
		case OPEN: {
			this.open();
			break;
		}
		case REFRESH: {
			this.refresh();
			break;
		}
		case RELOAD: {
			this.reload();
			break;
		}
		case GET_ITERATION: {
			this.getIteration();
			break;
		}
		case POST_PERSON: {
			this.postPerson();
			break;
		}
		case POST_PARAMETER: {
			this.postParameter();
			break;
		}
		case POST_PFSL: {
			this.postPossibleFiniteStateList();
			break;
		}
		case POST_PFSL_REORDER: {
			this.postPossibleFiniteStateListReorder();
			break;
		}
		case REMOVE_PARAMETER: {
			this.removeParameter();
			break;
		}
		case CLOSE: {
			this.close();
			break;
		}
		case RESTORE: {
			this.restore();
			break;
		}
		default: {
			System.out.println("Unrecognized command.");
		}
		}
	}

	/**
	 * Posts a reorder of elements in {@link PossibleFiniteStateList}
	 */
	private void postPossibleFiniteStateListReorder() throws TransactionException {
		if (this.session.getOpenIterations().isEmpty()) {
			System.out.println("At first an iteration should be opened");
			return;
		}

		var iteration = this.session.getOpenIterations().keySet().stream().findFirst();
		if (iteration.isPresent()) {
			var iterationClone = iteration.get().clone(false);
			var pfsl = iteration.get().getPossibleFiniteStateList().stream()
					.filter(x -> x.getName().equals("PossibleFiniteStateList1")).findFirst();

			if (pfsl.isEmpty()) {
				System.out.println("There is not a predefined PossibleFiniteStateList. Execute post_pfsl");
				return;
			}

			var pfslClone = pfsl.get().clone(true);

			// make sure keys are preserved
			var itemsMap = new HashMap<Object, Long>();
			pfsl.get().getPossibleState().toDtoOrderedItemList().forEach(x -> itemsMap.put(x.getV(), x.getK()));
			var orderedItems = new ArrayList<OrderedItem>();
			pfslClone.getPossibleState().getSortedItems().values().forEach(x -> {
				var orderedItem = new OrderedItem();
				orderedItem.setK(itemsMap.get(x.getIid()));
				orderedItem.setV(x);
				orderedItems.add(orderedItem);
			});

			pfslClone.getPossibleState().clear();
			pfslClone.getPossibleState().addOrderedItems(orderedItems);
			pfslClone.setModifiedOn(OffsetDateTime.now());

			pfslClone.getPossibleState().move(1, 0);
			var transaction = new ThingTransactionImpl(TransactionContextResolver.resolveContext(iterationClone),
					iterationClone);
			transaction.createOrUpdate(pfslClone);

			this.session.write(transaction.finalizeTransaction()).join();

			this.printCacheSize();

			this.printCommands();
		}
	}

	/**
	 * Posts a predefined {@link PossibleFiniteStateList}
	 */
	private void postPossibleFiniteStateList() throws TransactionException {
		if (this.session.getOpenIterations().isEmpty()) {
			System.out.println("At first an iteration should be opened");
			return;
		}

		var iteration = this.session.getOpenIterations().keySet().stream().findFirst();
		if (iteration.isPresent()) {
			var iterationClone = iteration.get().clone(false);
			var pfs1 = new PossibleFiniteState(UUID.randomUUID(), this.session.getAssembler().getCache(), this.uri);
			pfs1.setName("state1");
			pfs1.setShortName("s1");

			var pfs2 = new PossibleFiniteState(UUID.randomUUID(), this.session.getAssembler().getCache(), this.uri);
			pfs2.setName("state2");
			pfs2.setShortName("s2");

			var pfsList = new PossibleFiniteStateList(UUID.randomUUID(), this.session.getAssembler().getCache(),
					this.uri);
			pfsList.setName("PossibleFiniteStateList1");
			pfsList.setShortName("PFSL1");

			var domainOfExpertise = this.session.getOpenIterations().get(iteration.get()).getLeft();
			pfsList.setOwner(domainOfExpertise);

			var transaction = new ThingTransactionImpl(TransactionContextResolver.resolveContext(iterationClone),
					iterationClone);
			transaction.create(pfsList, iterationClone);
			transaction.create(pfs1, pfsList);
			transaction.create(pfs2, pfsList);

			this.session.write(transaction.finalizeTransaction()).join();

			this.printCacheSize();

			this.printCommands();
		}
	}

	/**
	 * Posts a predefined {@link Parameter}
	 */
	private void postParameter() throws TransactionException {
		if (this.session.getOpenIterations().isEmpty()) {
			System.out.println("At first an iteration should be opened");
			return;
		}

		var iteration = this.session.getOpenIterations().keySet().stream().findFirst();
		if (iteration.isPresent()) {
			var elementDefinition = iteration.get().getElement().get(0);
			var elementDefinitionClone = elementDefinition.clone(false);
			var domainOfExpertise = this.session.getOpenIterations().get(iteration.get()).getLeft();

			var parameter = new Parameter(UUID.randomUUID(), this.session.getAssembler().getCache(), this.uri);
			var parameterType = this.session.getAssembler().getCache().asMap().values().stream()
					.filter(x -> x instanceof ParameterType).findFirst().orElseThrow();

			parameter.setParameterType((ParameterType) parameterType);
			parameter.setOwner(domainOfExpertise);

			var transaction = new ThingTransactionImpl(
					TransactionContextResolver.resolveContext(elementDefinitionClone), elementDefinitionClone);
			transaction.create(parameter, elementDefinitionClone);

			this.session.write(transaction.finalizeTransaction()).join();

			this.printCacheSize();

			this.printCommands();
		}
	}

	/**
	 * Prints a text separator to the console
	 */
	private void printSeparator() {
		System.out.println("*********************************");
	}

	/**
	 * Opens a connection to a data-source specified by {@code uri}
	 */
	private void open() {
		this.session.open().join();
		this.printCacheSize();

		this.printCommands();
	}

	/**
	 * Refreshes data to be in sync with the data-source
	 */
	private void refresh() {
		if (this.isSiteDirectoryUnavailable()) {
			System.out.println("At first a connection should be opened.");
			return;
		}
		this.session.refresh().join();
		this.printCacheSize();

		this.printCommands();
	}

	/**
	 * Reloads data from the data-source
	 */
	private void reload() {
		if (this.isSiteDirectoryUnavailable()) {
			System.out.println("At first a connection should be opened.");
			return;
		}
		this.session.reload().join();
		this.printCacheSize();

		this.printCommands();
	}

	/**
	 * Closes connection to the data-source and end the execution of this app
	 */
	private void close() {
		if (this.isSiteDirectoryUnavailable()) {
			System.out.println("At first a connection should be opened.");
			return;
		}
		try {
			this.session.close().join();
		} catch (Exception ex) {
			System.out.println("During close operation an error is received: ");
			System.out.println(ex.getMessage());
		}

		this.printSeparator();
		System.out.println("Good bye!");
		this.printSeparator();
		this.isRunning = false;
	}

	/**
	 * Restores data on the data-source
	 */
	private void restore() throws URISyntaxException, ExecutionException, InterruptedException, IOException {
		if (!this.isSiteDirectoryUnavailable()) {
			System.out.println("It is possible to restore the server only before connection is opened.");
			return;
		}

		var uriBuilder = new URIBuilder(this.uri);
		uriBuilder.setPath("/Data/Restore");
		HttpAsyncClientBuilder httpClientBuilder = HttpAsyncClients.custom();

		httpClientBuilder
				.setDefaultHeaders(Collections.singletonList(new BasicHeader(HttpHeaders.AUTHORIZATION,
						"Basic " + Base64.getEncoder()
								.encodeToString(String
										.format("%s:%s", this.credentials.getUserName(), this.credentials.getPassword())
										.getBytes(StandardCharsets.US_ASCII)))));

		CloseableHttpAsyncClient client = httpClientBuilder.build();
		client.start();

		var post = new HttpPost(uriBuilder.build());
		client.execute(post, null).get();
		client.close();

		this.printCommands();
	}

	/**
	 * Removes the first found {@link Parameter} from the first found
	 * {@link ElementDefinition}
	 */
	private void removeParameter() throws TransactionException {
		if (this.session.getOpenIterations().isEmpty()) {
			System.out.println("At first an iteration should be opened");
			return;
		}

		var iteration = this.session.getOpenIterations().keySet().stream().findFirst();
		if (iteration.isPresent()) {
			var elementDefinition = iteration.get().getElement().get(0);
			var elementDefinitionClone = elementDefinition.clone(false);
			var parameterClone = elementDefinition.getParameter().get(0).clone(false);

			var transaction = new ThingTransactionImpl(
					TransactionContextResolver.resolveContext(elementDefinitionClone), elementDefinitionClone);
			transaction.delete(parameterClone, elementDefinitionClone);

			this.session.write(transaction.finalizeTransaction()).join();

			this.printCacheSize();

			this.printCommands();
		}
	}

	/**
	 * Retrieves {@link Iteration} related data of the first found {@link Iteration}
	 */
	private void getIteration() {
		var siteDirectory = this.session.getAssembler().retrieveSiteDirectory();
		if (this.isSiteDirectoryUnavailable()) {
			System.out.println("At first a connection should be opened.");
			return;
		}

		var engineeringModelIid = siteDirectory.getModel().get(0).getEngineeringModelIid();
		var iterationIid = siteDirectory.getModel().get(0).getIterationSetup().get(0).getIterationIid();
		var domainOfExpertiseIid = siteDirectory.getModel().get(0).getActiveDomain().get(0).getIid();

		var model = new EngineeringModel(engineeringModelIid, this.session.getAssembler().getCache(), this.uri);
		var iteration = new Iteration(iterationIid, this.session.getAssembler().getCache(), this.uri);
		iteration.setContainer(model);

		var topElement = iteration.getTopElement();
		var elements = iteration.getElement();

		var domainOfExpertise = new DomainOfExpertise(domainOfExpertiseIid, this.session.getAssembler().getCache(),
				this.uri);

		this.session.read(iteration, domainOfExpertise).join();

		var topElement2 = iteration.getTopElement();
		var elements2 = iteration.getElement();

		this.printCacheSize();

		this.printCommands();
		
		var it = this.session.getOpenIterations().keySet().stream().findFirst().get();
		for (var i : it.getElement()) {
			System.out.println(i.getName());
		}
		
		//System.out.println(it.getElement());
	}

	/**
	 * Posts a predefined {@link Person}
	 */
	private void postPerson() throws TransactionException {
		if (this.isSiteDirectoryUnavailable()) {
			System.out.println("At first a connection should be opened.");
			return;
		}

		// Create person object
		var person = new Person(UUID.randomUUID(), this.session.getAssembler().getCache(), this.uri);
		person.setActive(true);
		person.setShortName("M" + LocalDateTime.now().toString());
		person.setSurname("Mouse");

		var email1 = new EmailAddress(UUID.randomUUID(), this.session.getAssembler().getCache(), this.uri);
		email1.setValue("mikki.home@mouse.com");
		email1.setVcardType(VcardEmailAddressKind.HOME);

		person.setDefaultEmailAddress(email1);
		person.setGivenName("Mike");
		person.setPassword("password");

		var email2 = new EmailAddress(UUID.randomUUID(), this.session.getAssembler().getCache(), this.uri);
		email2.setValue("mikki.work@mouse.com");
		email2.setVcardType(VcardEmailAddressKind.WORK);

		var modifiedSiteDirectory = this.session.getAssembler().retrieveSiteDirectory().clone(true);

		var transaction = new ThingTransactionImpl(TransactionContextResolver.resolveContext(modifiedSiteDirectory),
				modifiedSiteDirectory);
		transaction.create(person, modifiedSiteDirectory);
		transaction.create(email1, person);
		transaction.create(email2, person);

		this.session.write(transaction.finalizeTransaction()).join();

		this.printCacheSize();

		this.printCommands();
	}

	/**
	 * Prints current amount of objects in the cache
	 */
	private void printCacheSize() {
		System.out.println(this.session.getAssembler().getCache().size() + " objects currently in the cache.");
	}

	/**
	 * Prints a list of available commands to the console
	 */
	private void printCommands() {
		this.printSeparator();
		System.out.println("Available commands:");
		System.out.println(OPEN + " - Open a connection to a data-source");
		System.out.println(REFRESH + " - Update the Cache with updated Things from a data-source");
		System.out.println(RELOAD + " - Reload all Things from a data-source for all open TopContainers");
		System.out
				.println(CLOSE + " - Close the connection to a data-source and clear the Cache and exits the program");
		System.out.println(RESTORE + " - Restores the state of a data-source to its default state");
		System.out.println(
				GET_ITERATION + " - gets a predefined iteration of an engineering model with dependent objects");
		System.out.println(POST_PERSON + " - posts a predefined person with 2 e-mail addresses");
		System.out.println(POST_PARAMETER + " - posts a predefined parameter");
		System.out.println(POST_PFSL + " - posts a predefined PossibleFiniteStateList with 2 PossibleFiniteStates");
		System.out.println(POST_PFSL_REORDER
				+ " - reorders(rotates in this particular case) states in the created predefined PossibleFiniteStateList (post_pfsl)");
		System.out.println(REMOVE_PARAMETER + " - removes a predefined Parameter of ElementDefinition");
		this.printSeparator();
	}

	/**
	 * Checks whether {@link SiteDirectory} is unavailable
	 */
	private boolean isSiteDirectoryUnavailable() {
		return this.session.getAssembler().retrieveSiteDirectory() == null;
	}
}
