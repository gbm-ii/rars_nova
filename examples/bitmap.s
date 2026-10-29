# RARS Nova graphics display demo
# gbm 10'2026

	.include	"syscalls.inc"

# graphics window dimensions
	.eqv WIDTH  960
	.eqv HEIGHT 540

	.data

	.text
	.globl main

	.macro syscall %number
	li a7, %number
	ecall
	.end_macro

# register usage:
# s0 - width constant
# s1 - height constant
# s2 - image buffer base

main:
	li s0, WIDTH
	li s1, HEIGHT
	# determine required memory size
	mul	s4, s0, s1	# no. of pixels
	slli	a0, s4, 2	# no. of bytes for sbrk
	# allocate screen memory
	syscall	SYS_SBRK
	
	mv	s2, a0	# store image base

rand_seed:
	li a0, 0
	csrr a1, time
	syscall SYS_RAND_SEED

	# create graphics window
	mv a0, s2
	li a1, WIDTH
	li a2, HEIGHT
	syscall SYS_BITMAP_DISPLAY

	# fill screen with random noise
	mv	t0, s2	# t0 - image buffer pointer
	mv t2, zero	# t2 - current y
	
row_loop:
	mv t1, zero	# t1 - current x

column_loop:
	li a0, 0
	syscall SYS_RAND_INT

	sw a0, (t0)	# write pixel
	addi t0, t0, 4	# move pointer

	addi t1, t1, 1
	bne t1, s0, column_loop
	
	addi t2, t2, 1
	bne t2, s1, row_loop

	# clear the screen
	mv	t0, s2
	mv	t1, s4
clear:
	sw	zero, (t0)	# write pixel
	addi	t0, t0, 4
	addi	t1, t1, -1
	bnez	t1, clear
	
fin:
	syscall	SYS_EXIT0
