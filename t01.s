.data
TRUE: .asciiz "true"
FALSE: .asciiz "false"
ENDL: .asciiz "\n"
.text
.globl main
main :
move $fp, $sp
sub $sp, $sp, 8
sub $sp, $sp, 8
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li.s $f0, 2.4
lw $t1, 0($sp)
add $sp, $sp, 4
s.s $f0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
l.s $f0, 0($t0)
cvt.w.s $f0, $f0
mfc1 $t0, $f0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
l.s $f0, 0($t0)
li $v0, 2
mov.s $f12, $f0
syscall
la $a0, ENDL
li $v0, 4
syscall
sub $t0, $fp, 16
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 8
li $v0, 10
syscall
