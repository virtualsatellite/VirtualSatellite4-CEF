#!/bin/bash

#/*******************************************************************************
# * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Eclipse Public License 2.0 which is available at
# * http://www.eclipse.org/legal/epl-2.0.
# *
# * SPDX-License-Identifier: EPL-2.0
# *******************************************************************************/

# --------------------------------------------------------------------------
# This script handles all upload activities of the project Virtual Satellite
# --------------------------------------------------------------------------

# Store the name of the command calling from commandline to be properly
# displayed in case of usage issues
COMMAND=$0

# This method decrypts the SSH secret to upload it to sourceforge
sourceforgeDecryptSecret() {
	eval "$(ssh-agent -s)"
	mkdir -p -m 700 /tmp/.sourceforge_ssh
	openssl aes-256-cbc -K $DECRYPT_KEY -iv $DECRYPT_IV -in id_ed25519.enc -out /tmp/.sourceforge_ssh/id_ed25519 -d
	chmod 600 /tmp/.sourceforge_ssh/id_ed25519
	ssh-add /tmp/.sourceforge_ssh/id_ed25519
}

# this method gives some little usage info
printUsage() {
    echo "usage: ${COMMAND} -k SECRET -i SECRET -u [swtbot|development|integration|release]"
}

uploadSwtBot() {
	rsync -e ssh -avP $TRAVIS_BUILD_DIR/swtbot/  dlrscmns@frs.sourceforge.net:/home/frs/project/virtualsatellite/VirtualSatellite4-CEF/swtbot/
}

uploadDevelopment() {
	rsync -e ssh -avP --delete $TRAVIS_BUILD_DIR/deploy/unsecured/p2/VirSat4_CEF_Application/development/  dlrscmns@frs.sourceforge.net:/home/frs/project/virtualsatellite/VirtualSatellite4-CEF/development/
	rsync -e ssh -avP --delete $TRAVIS_BUILD_DIR/deploy/unsecured/bin/VirSat4_CEF_Application/development/  dlrscmns@frs.sourceforge.net:/home/frs/project/virtualsatellite/VirtualSatellite4-CEF/bin/development/
}

uploadIntegration() {
	rsync -e ssh -avP $TRAVIS_BUILD_DIR/deploy/unsecured/p2/VirSat4_CEF_Application/integration/  dlrscmns@frs.sourceforge.net:/home/frs/project/virtualsatellite/VirtualSatellite4-CEF/integration/
	rsync -e ssh -avP $TRAVIS_BUILD_DIR/deploy/unsecured/bin/VirSat4_CEF_Application/integration/  dlrscmns@frs.sourceforge.net:/home/frs/project/virtualsatellite/VirtualSatellite4-CEF/bin/integration/
}

uploadRelease() {
	rsync -e ssh -avP $TRAVIS_BUILD_DIR/deploy/secured/p2/VirSat4_CEF_Application/release/  dlrscmns@frs.sourceforge.net:/home/frs/project/virtualsatellite/VirtualSatellite4-CEF/release/
	rsync -e ssh -avP $TRAVIS_BUILD_DIR/deploy/secured/bin/VirSat4_CEF_Application/release/  dlrscmns@frs.sourceforge.net:/home/frs/project/virtualsatellite/VirtualSatellite4-CEF/bin/release/
}


# process all command line arguments
while [ "$1" != "" ]; do
    case $1 in
        -k | --key )            shift
                                DECRYPT_KEY=$1
                                ;;
        -i | --iv )    			shift
                                DECRYPT_IV=$1
                                ;;
        -u | --upload ) 		shift
                                UPLOAD=$1
                                ;;
        -h | --help )           printUsage
                                exit
                                ;;
        * )                     printUsage
                                exit 1
    esac
    shift
done

# Setup ssh with sourceforge secrets
sourceforgeDecryptSecret

# Decide what to upload
case $UPLOAD in
    swtbot )           	uploadSwtBot
    					exit
                        ;;
    development )      	uploadDevelopment
    					exit
                        ;;
    integration )      	uploadIntegration
    					exit
                        ;;
    release )      		uploadRelease
    					exit
                        ;;
    * )                 printUsage
                        exit 1
esac
