## SEADS Android App
CMPS115 Fall 2016

## Build Status
| Branch | Jenkins Builder | 
|--------|---------|
| master | [![Build Status](http://24.4.229.205:8080/buildStatus/icon?job=SEADS)](http://24.4.229.205:8080/job/SEADS/) | 

[Unit Tests](http://24.4.229.205:8080/job/SEADS/ws/SEADS_V2/app/build/reports/tests/testReleaseUnitTest/index.html)

[Documentation](http://24.4.229.205:8080/job/SEADS/ws/SEADS_V2/build/docs/overview-tree.html)

[APK](http://24.4.229.205:8080/job/SEADS/ws/SEADS_V2/app/build/outputs/apk/release/app-release.apk)

## Github how-to
### Setup
1. Fork the repo from https://github.com/seadsystem/android into your account
2. cd ~/Documents
3. mkdir CMPS115
4. cd CMPS115
5. git clone (URL of your forked repo)
6. cd android
7. git remote add upstream https://github.com/seadsystem/android

### Daily Work
1. git checkout master
2. git pull upstream master
3. git checkout -b myworkbranch
4. ...make changes...
5. git add -A
6. git commit -m "explanation of changes"
7. git fetch upstream
8. git rebase upstream/master
9. git push origin myworkbranch
10. Login to http://github.com
11. Create a Pull Request from your repo/myworkbranch to upstream/master
12. Send message on Slack with comments to reviewers to review the Pull Request.
13. A Reviewer will send you comments or merge your change.
   - If merged, you are done!
   - If comments are sent back
      1. git checkout myworkbranch
      2. ...make changes...
      3. git add -A
      4. git commit -m "explanation of changes"
      5. git fetch upstream
      6. git rebase upstream/master -i
      7. Follow the instructions to squash your new change into your previous change
      8. change pick to squash for the change you just commited
      9. remove the commit message when it comes up.
      10. git push -f origin myworkbranch

## User Installation Guide
### Users with Development Environment
1. Set up local git repo according to instruction in the previous "How-to" section
2. Build the Android application (Ex. via AndroidStudio and Gradle) and produce APK file
3. Choose between signed vs unsigned APK when deciding if you want to share the APK with others
4. Load the APK file onto your phone and find it in the file system
5. Starting the APK file will initiate the instalation of SEADS Android Application
6. Start the SEADS Android Application and enjoy saving thousands of $$$ in electricity bills

### Users with Access to Pre-compiled APK
1. See previous section and start process at step 3

