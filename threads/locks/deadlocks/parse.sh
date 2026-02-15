#!/bin/bash

if [ $# -lt 1 ]; then
	echo "Usage $0 <File name containing Thread Dump>"
	exit 1
fi

tdFile="$1"

waitingArray=()
lockedArray=()


for waiting in `cat $tdFile |sed -n '/BLOCKED/,/^$/p'|  grep -zoP '(?<=waiting to lock <).*?(?= )' | tr '\076' '\012'`
do
	waitingArray+=(${waiting}) 
done


for locked in `cat $tdFile |sed -n '/BLOCKED/,/^$/p'|  grep -zoP '(?<=locked <).*?(?= )' | tr '\076' '\012'`
do
	lockedArray+=(${locked}) 
done




possibleDeadlock="false"
if printf "%s\n" "${waitingArray[@]}" | grep -qFxxf <(printf "%s\n" "${lockedArray[@]}"); then
    #echo "At least one element from waitingArray is in lockedArray."
    if printf "%s\n" "${waitingArray[@]}" | grep -qFxxf <(printf "%s\n" "${lockedArray[@]}"); then
	    possibleDeadlock="True"
    fi
fi

if [ "${possibleDeadlock,,}" == "true" ]; then
	echo "There is a deadlock" 
	echo "Check in file $tdFile for the details"
	echo "There is a circular lock:"
        echo "The list of object ids which are trying to acquire lock: ${waitingArray[@]}"
        echo "The list of object ids which are locked: ${lockedArray[@]}"
else
	echo "Did not find deadlock" 
fi
