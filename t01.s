.data
TRUE: .asciiz "true"
FALSE: .asciiz "false"
ENDL: .asciiz "\n"
.text
.globl main
main :
move $fp, $sp
sub $sp, $sp, 8
sub $sp, $sp, 4
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li.s $f0, 3.2
lw $t1, 0($sp)
add $sp, $sp, 4
s.s $f0, 0($t1)
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
l.s $f0, 0($t0)
sub $sp, $sp, 4
s.s $f0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 12
jal L0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 16
lw $t1, 0($sp)
add $sp, $sp, 4
s.s $f0, 0($t1)
sub $t0, $fp, 12
l.s $f0, 0($t0)
li $v0, 2
mov.s $f12, $f0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 4
li $v0, 10
syscall
L0 :
sub $sp, $sp, 0
sub $t0, $fp, 4
l.s $f0, 0($t0)
sub $sp, $fp, 12
jr $ra
add $sp, $sp, 0
sub $sp, $fp, 3
jr $ra
