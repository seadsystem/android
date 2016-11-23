## SEADS Android App
CMPS115 Fall 2016

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
