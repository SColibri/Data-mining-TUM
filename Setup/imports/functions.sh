#!/bin/bash

# Check if package is installed
# <returns>int value; 0 - false, 1 - true</returns>
isInstalled()
{
    REQUIRED_PKG=$1
    PKG_OK=$(dpkg-query -W --showformat='${Status}\n' $REQUIRED_PKG|grep "install ok installed")
    if [ "" = "$PKG_OK" ] 
    then
        # return false
        echo "0"
    else
        # return true
        echo "1"
    fi
}

