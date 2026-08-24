# ASCII-Webcam-Art
A Java program that converts frames from the default webcam into ASCII art.

Idea from: https://robertheaton.com/2018/06/12/programming-projects-for-advanced-beginners-ascii-art/

## About the Project
ASCII stands for American Standard Code for Information Interchange. ASCII art is a method of creating images using printed characters (numbers, letters, punctuation marks, or other symbols). To generate ASCII art using real-time video, I wrote a program that extracts RGB values and calculates a brightness level for each pixel. The calculation uses the luminance formula, which is common for ASCII art generation. Based off this brightness level, the program maps a character to each pixel. This results in a full ASCII style images for real-time frames from the webcam. There is multiple ways to view the images, but I chose Windows Powershell.

### Windows Powershell
After running the program and changing the directory to the project folder, I ran these commands in Windows Powershell to view the results:
```
while ($true) {
       [Console]::SetCursorPosition(0,0)
       Get-Content result.txt
       Start-Sleep -Seconds 1
   }
```
## ASCII characters from lightest to darkest
`^",:;Il!i~+_-?][}{1)(|\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$

## Example
Here's an image of myself printed in Powershell. I'm still working on improving the contrast.

<img width="900" height="910" alt="image" src="https://github.com/user-attachments/assets/59599b80-2e16-4fe6-91ea-81c0bf895f0d" />

