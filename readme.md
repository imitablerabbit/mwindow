<h1>MWindow</h1>
<p>Alternative design for Java swing</p>
<strong>NOTE: This package has only been tested and working on windows</strong>
<br>
<strong>AIMS: To create a simplified yet appealing alternative for the current Java swing</strong>
<h2>Version 1.0:</h2>
<strong>To Do:</strong>
<ul>
    <li>Create MTextField class</li>
    <li>Create a MOptionPane class for easy message windows:
        <ul>
            <li>Add a new method to create an information box</li>
            <li>Create images for the warning and information boxes</li>
        </ul>
    </li>
</ul>
<strong>Change log:</strong>
<br>
<ul>
    <li>Created MLabel class</li>
    <li>Created MTextArea class</li>
    <li>Implemented getters and setters for the MDragWindow</li>
    <li>Created a global colours class (static) for easy change of colours for all components in the package (Sweep through the classes to find ones which use old colour values).</li>
    <li>MDragWindow:
        <ul>
            <li>Removed need for revalidation after a component is added by overriding add()</li>
        </ul>
    </li>
    <li>MOptionPane:
        <ul>
            <li>Added a new method to create message box</li>
            <li>Added a new method to create a warning box</li>
            <li>Created a basic message box method (unfinished)</li>
        </ul>
    </li>
    <li>Changed the way windows close so that one window does not close all other windows</li>
    <li>MButton:
        <ul>
            <li>Added option to change position of label</li>
            <li>Added option to change font of the label</li>
        </ul>
    </li>
    <li>Added easy implementation into eclipse</li>
    <li>Changed visibility of classes to public</li>
</ul>
<h2>Screenshots:</h2>
<strong>Drawing Application</strong>
![Drawing Application](screenshots/drawing-application.png?raw=true)
