VirSat HotFix Release Version 4.x.y

This ticket captures all release related work for creating a HotFix Release

Check and create master_LTS branch:
- **Information!!!** In case the Version that needs to be HotFixed is ending with 0 such as 4.3.0 then there is probably no LTS branch yet. In that case create a new LTS branch strating from the 4.3.0 Release - TAG following the naming cponvention. In case the HotFix is for a 4.3.1 the LTS already exists and does not need to be created.
- [ ] LTS branch exists or has been created

Create integration branch:
- [ ] Remove current integration branch (Make sure no one else is integrating at the moment) 
- [ ] Create new integration branch from Release-TAG of the Version to be HotFixed branch
- [ ] Run ant script to update version numbers
- [ ] Update the target platform (integration) with all new versions referencing VirSat 4 Core and other resources
- [ ] Commit new version number to integration branch
- [ ] Apply all needed fixes

Update LTS/release branch:
- [ ] Update the target platform (release) with all new versions referencing VirSat 4 Core and other resources
- [ ] Commit new version number to integration branch
- [ ] Merge integration branch into master_LTS branch (Merge Request)
- [ ] Create Release Tag and update Jenkins configuration
- [ ] Run Jenkins VirSat CEF Release build
 
Merge back integration branch:
- [ ] Merge integration branch into development branch 
 
Well Done!! You should have a new Virtual Satellite HotFix Release :rocket:

