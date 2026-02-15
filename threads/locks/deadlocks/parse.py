import sys
import re

if len(sys.argv) < 2:
    print(f"Usage {sys.argv[0]} <File name containing Thread Dump>")
    sys.exit(1)

tdFile = sys.argv[1]

waitingArray = []
lockedArray = []

with open(tdFile, 'r', encoding='utf-8') as f:
    content = f.read()

# Extract the BLOCKED sections
blocked_sections = re.findall(r'BLOCKED.*?(?=\n\s*\n|$)', content, re.DOTALL)

for section in blocked_sections:
    # Find all waiting to lock <...> occurrences
    waiting_matches = re.findall(r'waiting to lock <(.*?) ', section)
    for waiting in waiting_matches:
        # Replace ':' with '\n' equivalent by splitting on ':' and extending list
        waitingArray.extend(waiting.split(':'))

    # Find all locked <...> occurrences
    locked_matches = re.findall(r'locked <(.*?) ', section)
    for locked in locked_matches:
        lockedArray.extend(locked.split(':'))

# Remove empty strings if any
waitingArray = [w for w in waitingArray if w]
lockedArray = [l for l in lockedArray if l]

possibleDeadlock = False
# Check if any element in waitingArray is also in lockedArray
if set(waitingArray) & set(lockedArray):
    possibleDeadlock = True

if possibleDeadlock:
    print("There is a deadlock")
    print(f"Check in file {tdFile} for the details")
    print("There is a circular lock:")
    print(f"The list of object ids which are trying to acquire lock: {waitingArray}")
    print(f"The list of object ids which are locked: {lockedArray}")
else:
    print("Did not find deadlock")
