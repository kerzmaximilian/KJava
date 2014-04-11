KJava
=====
A simple-to-use, easily custamisable graphical user interface.
------------------------------------------------------------------


KJava is initiated by creating a new KDisplay object within the main(String args0) function.

Components are added to the screen by creating a new object of the component in question, 
modifying it to the users wishes, and adding it to the int[] screen array.

--------------------------------------------------------------------------

Example: Creating a rectangle

KRect rect = new KRect(posX, posY, width, height);
rect.fill(new Color(200,200,0);
screen = rect.add(screen);

--------------------------------------------------------------------------

For components which allow user interaction an Action Listener should be added.

Example: Creating a button

KButton button = new KButton(posX, posY, width, height);
button.setTitle("Title");
button.addActionListener();
screen = button.add(screen);

--------------------------------------------------------------------------

For components which make use of key inputs the KKeyEvent object which is initiated by the KDisplay class automatically,
denoted as KE, must be manually added.

Example: using KE for test inputs in KWrapper

KWrapper wrapper = new KWrapper(posX, posY, width, height);
wrapper.setBorder(true);
wrapper.setCursor(true);

String input = "";
input = wrapper.setText(input, KE);

wrapper.addActionListener();
screen = wrapper.add(screen);

--------------------------------------------------------------------------

Other oobjects allow visual data representations, lists and panels for folder, image and graph representations.

In case of questions, please do not hesitate to ask.
