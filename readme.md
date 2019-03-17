# The Instruction Processing Microservice
The instruction-processing-service intends to offer distributed and parallel 
processing.


## Description
This is a different concept in Microservices where the code to be executed 
by this Microservice is sent to the Microservice by an InstructionRequest
Multiple instructionScripts send in a InstructionRequest are each executed
thi their own ForthProcessor thread.


### The Processor is a Virtual CPU with its MicroInstructions running Forth
A Processor is implemented which uses Forth as its Microinstruction Language

### The Scripts executed 
The InstructionRequest send a List of instructionScripts to this service
to be executed by its Virtual CPU as "Forth Programs"

### The CPU is Multythreaded
Virtual CPU spawns a ForthProcessor thread to run each instructionScripts

### Multiple BIOS's to select
Unlike your CPU in your laptop at runtime we choose which BIOS's we want 
The BIOS's provide libraries for providing the following functionality:
- MATHS_BIOS (library)
- BUSINESS_BIOS (Calculator library)
- ELECTRICAL_ENGINEERING_BIOS (library)
- CHEMICAL_ENGINEERING_BIOS (library)
- ASRTONOMY_BIOS (library)

## I/O supported
The InstructionRequest has a Map : ioMap
and a List : ioVariables this identifies the variables used in the abover map

### File I/O
You define File and Folder specifications which will be defined in the 
above variables : 
ioMap and ioVariables

### Database I/o
You define Database connections , cridentials , and tables  which will 
be defined in the above variables : 
ioMap and ioVariables

### Message Queues
You define Queue servers , queues , and credentials which will 
be defined in the above variables : 
ioMap and ioVariables

### AES Encryption provided for I/O
AES Cyptography is provided by this service to decrypt the I/O variables

This is also provided for encrypting messages to be sent to Message Queues

Encryption key and SALT will be sent in the  to decrypt the values in the 

The InstructionRequest has these two fields used for decryption
aesEncryptionKey
aesSalt

They will not have key values but tell the instruction-processing-service 
which encryption Key and Salt to use.


### Join the team
This is an exciting project that promises to teach  
Send email to :
Architect : Nico Michael : email :  nicomichael2018 AT yahoo DOT COM 
