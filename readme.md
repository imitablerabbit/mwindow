<h1>mwindow</h1>

<h2>Alternative design for Java swing</h2>

<strong>NOTE: This package has only been tested and working on windows
AIMS: To create a simplified yet appealing alternative for the current Java swing</strong>

<h4>Version 1.0:</h4>

To Do:
  - Create MTextArea class
  - Create MTextField class
  - Create a global colours class (static) for easy change of colours for all components in the package (Sweep through the classes to find ones which use old colour values)
  - Create a MOptionPane class for easy message windows:
    - Add a new method to create an information box
    - Create images for the warning and information boxes
  - Implement getters and setters for the MDragWindow  

Change log:
  - MDragWindow:
    - Removed need for revalidation after a component is added by overriding add()
  - MOptionPane:
    - Added a new method to create message box
    - Added a new method to create a warning box
    - Created a basic message box method (unfinished)
  - Changed the way windows close so that one window does not close all other windows
  - MButton:
    - Add option to change position of label
    - Add option to change font of the label
  - Added easy implementation into eclipse
  - Changed visibility of classes to public
  
