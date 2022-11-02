# Creating a VM on cc LRZ
Creating a virtual machine can be done by following the lrz instructions [here](https://doku.lrz.de/display/PUBLIC/Create+a+VM)

# Known issues
## Timeout port 22
|Possible cause|Solution|
|--|---|
|Firewall was not set-up correctly| Solution -> goto security groups > edit > manage rules > add rule Create a rule with "security group" selected on the remote option and create.|

## Permission denied
|Possible cause|Solution|
|--|---|
|ssh key does not have the correct permissions|change the ssh private key permissions by using chmod on linux or use the ssh command "ssh-keygen" and paste your key inside this newly created file (since this file has the correct security options set-up for you)|
|User does not have permission to run the ssh command| Make sure you have admin privileges (on linux use the sudo command)|
