# RARS Nova graphics display

RARS Nova introduces the new graphics diplay window support.
The graphics window size is set by user. The window is created by a system call.

## Image buffer

Image buffer memory is allocated by user, either statically or from the ststem heap.
To support reasonable window sizes, the data region allocation limit is increaded to 16 MiB (from $ MiB in the original RARS).
Each pixel is represented by a 32-bit word, with top 8 bits unused. The first word of the buffer stores the top-left pixel.

## Graphics window syscall

Syscall 61 is used to create and open the graphics window. The call requires three arguments:
- a0 - starting address of image memory
- a1 - window width in pixels
- a2 - window height in pixels

## Example code

The example code may be found in exmples/bitmap.s. The program:
- allocates the screen memory using sbrk
- creates the graphics window using display_bitmap
- fills the image with random noise
- clears the image

## Remarks

Java runtime uses image scaling dependent of physical screen DPI. When using high density displays, the pixels are scaled up - enlarged.



