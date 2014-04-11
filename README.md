KJava
=====
A simple-to-use, easily custamisable graphical user interface.
------------------------------------------------------------------


KJava is initiated by creating a new KDisplay object within the main(String args0) function.

Components are added to the screen by creating a new object of the component in question, 
modifying it to the users wishes, and adding it to the int[] screen array.

--------------------------------------------------------------------------

Example: Creating a rectangle\n\n

KRect rect = new KRect(posX, posY, width, height);\n
rect.fill(new Color(200,200,0);\n
screen = rect.add(screen);\n

--------------------------------------------------------------------------

For components which allow user interaction an Action Listener should be added.\n\n

Example: Creating a button\n\n

KButton button = new KButton(posX, posY, width, height);\n
button.setTitle("Title");\n
button.addActionListener();\n
screen = button.add(screen);\n

--------------------------------------------------------------------------

For components which make use of key inputs the KKeyEvent object which is initiated by the KDisplay class automatically,
denoted as KE, must be manually added.\n\n

Example: using KE for test inputs in KWrapper\n\n

KWrapper wrapper = new KWrapper(posX, posY, width, height);\n
wrapper.setBorder(true);\n
wrapper.setCursor(true);\n\n

String input = "";\n
input = wrapper.setText(input, KE);\n

wrapper.addActionListener();\n
screen = wrapper.add(screen);\n

--------------------------------------------------------------------------

Other oobjects allow visual data representations, lists and panels for folder, image and graph representations.

In case of questions, please do not hesitate to ask.
