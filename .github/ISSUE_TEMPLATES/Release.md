Virtual Satellite Release Version 4.x.x

This ticket captures all release related work of the VirtualSatellite4-CEF release

1. Perform version update:
- [ ] Checkout/Update the Development branch
- [ ] Remove current integration branch (Make sure no one else is integrating at the moment) 
- [ ] Create new integration branch from development branch
- [ ] Run ant script to update version numbers
- [ ] Regenerate concept.xmi of all relevant projects
- [ ] Launch application from product configuration and resolve all dependency issues
- [ ] Merge integration branch into development branch (Pull Request named "Integration 4.x.x - Remerge Versions")

2. Perform integration on integration branch:
- [ ] Apply all needed fixes
- [ ] Update the release notes

3. Update master/release branch:
- [ ] Merge integration branch into master branch (Pull Request named "Release 4.x.x")
- [ ] Create Release Tag
 
4. Merge back integration branch:
- [ ] Merge integration branch into development branch (Pull Request named "Integration 4.x.x - Remerge Fixes")
 
Well Done!! You should have a new Virtual Satellite CEF Release :rocket:

