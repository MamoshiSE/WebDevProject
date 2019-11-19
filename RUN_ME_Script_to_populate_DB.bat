@echo off
echo Running populateDB.js..
mongo localhost:27017/shop --quiet populateDB.js
echo DONE
pause