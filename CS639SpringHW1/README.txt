{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf100
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 Description:\
\
Your objective for this assignment is to create a Color Picker app that changes the color of the selected animal image present on the screen. \
\
App Requirements:\
-The three animals in your app will be a bird, a cat and a dog. The animals on the screen should be stacked vertically with the row containing each animal taking up the same amount of vertical space, regardless of what phone the user is using. Next to each animal image should be a brief description or a fun fact about the animal.\
- When the app starts, all three animals should be visible to the user with their descriptions hidden. At the bottom of the screen should be a color picker, centered horizontally, with 5 colors of your choice.\
- The format of the color picker should be as follows: 5 squares with dimensions no greater than 50dp x 50dp. The colors that appear in the color picker are up to you.\
- When a user clicks on an animal image, the description of the selected animal should appear on the screen. All other animal descriptions should disappear.\
- When a user clicks on a color from the color picker, the selected animal's image should change to the selected color. The images of the other animals should remain unchanged.\
- If no image is selected, present the user with a Toast message letting them know that they have to select an image before choosing a color.\
\
Grading Distribution\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0
\cf0 70% = Completion. An app that meets all of the above requirements will be considered code complete\
10% = Code Clarity. Make sure your code is easy to follow and understand.\
10% = Documentation/Comments. Provide occasional documentation to ensure that you fully grasp what your code is doing\
10% = Efficiency/Number of Lines. The less lines of code you use, the better. Find a balance between readability and being concise.\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0
\cf0 \
Helpful Hints:\
- The method setColorFilter in the ImageView class is the easiest way to change an image's color. The documentation for it exists here: {\field{\*\fldinst{HYPERLINK "https://developer.android.com/reference/android/widget/ImageView.html#setColorFilter(int,%20android.graphics.PorterDuff.Mode)"}}{\fldrslt https://developer.android.com/reference/android/widget/ImageView.html#setColorFilter(int, android.graphics.PorterDuff.Mode)}}. Pass in PorterDuff.Mode.SRC_IN for the second argument.\
- For a comprehensive list of colors in hex format, you can visit this link: {\field{\*\fldinst{HYPERLINK "https://web.njit.edu/~kevin/rgb.txt.html"}}{\fldrslt https://web.njit.edu/~kevin/rgb.txt.html}}. Use the values in the RGB Hex column in your Android app.}