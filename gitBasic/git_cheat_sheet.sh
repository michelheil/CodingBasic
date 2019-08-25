Using git / cheat sheet

# figure out which version is installed
git --version

# figure out where bin/git is located
which git

# figure out where your .gitconfig (global git configuration) is located
# simply open the config with 
git config --global --edit
# and check the path of the file in the editor
# in my case it is located at /home/michael/.gitconfig

# ensure that git knows your name (and your email address)
git config --global user.name "Michael"
git config --global user.email "michelheil@gmx.de"

# for better and faster handling you can define alias
# the following alias "st" allows you to use "git st" instead of "git status"
git config --global alias.st status

# other useful aliases
git config --global alias.ci commit
git config --global alias.br branch
git config --global alias.co diff
git config --global alias.he help
git config --global alias.cl clone

# change color when working with git on command line
git config --global color.ui auto

# setting up end-of-line format to unix "lf". Other options are "crlf" (used in Windows) or "native" (system default value)
git config --global core.eol lf
# there are some more config settings availabe when working with Windows-edited files. However, this is quite a big hussle. So keep on Linux :-)

# define standard editor for commit messages
git config --global core.editor vi


# global configuration of ignoring files
git config --global core.excludefile ~/.gitignore

# local configuration within the actual local repository of ignoring files
echo "*.tmp" >> .gitignore


########################################################################
# Creating your first repository and link it to GitHub
########################################################################

# Follow these steps:
### Create a repository in GitHub, for example called "gitCheatSheet". This will be your remote repository
### Save this file where you want to have your local repository, e.g. /home/michael/git-repositories/gitCheatSheet

# Add a README file
echo "# gitCheatSheet" >> README.md

# Initialize git repository 
cd ~/git-repositories/gitCheatSheet
git init
# repsone:
# Initialized empty Git repository in /home/michael/git-repositories/gitCheatSheet/.git/

# Add this file and the README.md to the repository
git status

git add README.md
git add git_cheat_sheet.sh 

git status

# Do a commit and push to remote repository
git commit -m "first commit"

# Create a new GitHub repository (DO NOT INITIALIZE) and copy URL into next command
git remote add origin https://github.com/michelheil/gitCheatSheet.git
git push -u origin master

# type in GitHub username and password

