#!/bin/bash
set -e # exit with nonzero exit code if anything fails

# Delete repo folder, force success if it doesn't exist
rm -rf repo || exit 0;

# Clone plugin repo and copy build artifact
git clone "https://${GH_TOKEN}@${GH_REF}" repo
cp target/Slacker.jar repo/src/android/libs/Slacker.jar

# go to repo dir
cd repo

# Inside this git repo we'll pretend to be a new user
git config user.name "Travis CI"
git config user.email "frederick.jansen@gmail.com"

# Add libary and commit
git add ./src/android/libs/Slacker.jar
git commit -m "Upload new Android client library"

# Push to master of plugin repo
# /dev/null to hide any sensitive credential data that might otherwise be exposed.
git push --quiet "https://${GH_TOKEN}@${GH_REF}" origin master > /dev/null 2>&1