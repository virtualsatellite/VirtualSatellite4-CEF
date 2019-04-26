VirSat Release Version 4.x.x

This ticket captures all release related work of the virsat 4 CEF release

Create integration branch:
- [ ] Checkout/Update the Development branch
- [ ] Run ant script to update version numbers
- [ ] Update the target platform (development, integration) with all new versions referencing VirSat 4 Core and other resources
- [ ] Commit new version number to development branch
- [ ] Remove current integration branch (Make sure no one else is integrating at the moment) 
- [ ] Create new integration branch from development branch
- [ ] Apply all needed fixes
- [ ] Update the release notes
- [ ] Launch application from product configuration and resolve all dependency issues

Update master/release branch:
- [ ] Update the target platform (release) with all new versions referencing VirSat 4 Core and other resources
- [ ] Merge integration branch into master branch (Merge Request)
- [ ] Create Release Tag and update Jenkins configuration with new tag
- [ ] Run Jenkins VirSat Release build
 
Merge back integration branch:
- [ ] Merge integration branch into development branch 
 
Well Done!! You should have a new Virtual Satellite Release for the CEF :rocket: