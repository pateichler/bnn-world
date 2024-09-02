# BNN World

BNN World is the genetic algorithm simulation for [BNN](https://github.com/pateichler/bnn-world).

## Usage

The project is managed using Maven. The project has two dependencies that will need to be installed yourself:

- [java-config-generator](https://github.com/pateichler/java-config-generator)
- [bnn](https://github.com/pateichler/bnn)

After Maven compiles the project, there should be defaultSettings.json generated in the classes folder. Copy this to root folder and edit the file to change settings. Then run it using the main function in class Runner.

The program will then run until the max generations is reached. Outputs of the program are stored in the experiments folder with an increasing experiment number.