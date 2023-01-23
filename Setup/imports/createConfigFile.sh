#!/bin/bash

# Creates the config file for easy ssh connection and naming
createConfigFile()
{
    TEMP_CURRENTPATH=$1
    USERNAME=$2
    PATH_CONFIG=$3/configTest
    START_IP_LINES=3

    fs_backup=$IFS
    IFS=$(echo -en "\n\b")

    # create file if it does not exist
    touch $PATH_CONFIG
    truncate -s 0 $PATH_CONFIG

    # load template
    readarray TEMPLATE_LINES -t < $TEMP_CURRENTPATH/Templates/configTemplate

    # load ip list
    readarray HOSTS_LINES -t < $TEMP_CURRENTPATH/ModifyThese/ipList.txt

    for line in ${HOSTS_LINES[@]}
    do
        
        if [[ $line != *"#"* ]]
        then
            echo $line \n
            IFS=' ' read -ra items <<< $line

            echo ${TEMPLATE_LINES[0]/"<name>"/${items[0]}} >> $PATH_CONFIG
            echo ${TEMPLATE_LINES[1]/"<username>"/"$USERNAME"} >> $PATH_CONFIG
            echo ${TEMPLATE_LINES[2]/"<ip>"/${items[1]}} >> $PATH_CONFIG 
            echo ${TEMPLATE_LINES[3]} >> $PATH_CONFIG
            echo ${TEMPLATE_LINES[4]} >> $PATH_CONFIG
            echo "" >> $PATH_CONFIG
        fi
    done

    IFS=fs_backup
}