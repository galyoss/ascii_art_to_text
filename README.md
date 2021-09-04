 #ascii to text
 
 ```
     _    ____   ____ ___ ___   _____ ___   
    / \  / ___| / ___|_ _|_ _| |_   _/ _ \  
   / _ \ \___ \| |    | | | |    | || | | | 
  / ___ \ ___) | |___ | | | |    | || |_| | 
 /_/   \_\____/ \____|___|___|   |_| \___/    ....  TEXT
 ```
                                            
Written by Gal Yossifov

Run Instructions:
Download the repository and Run the command

```
mvn compile exec:java -Dexec.mainClass="Exec" -Dexec.args="<file_to_parse>, <optional: special config file>" 
```

General Instructions:
This program translates Text files including numbers written in ascii art, to normal readable text.
The Default input specs are:
  - text file includes only the characters: ' ', '|', '_'
  - each "block" is built from 3 lines including those signs, and 1 empty line
  - each line that is not empty includes 27 characters excatly.
  
 For generic use, I've added an option to re-config the dictionary from ascii-art to numbers and symbols.
 if you use a special config file, please notice the format: first line is for the string value to be filled, followed by 3 lines including the ascii art.
 ```
3
 _ 
 _|
 _|
 ```


