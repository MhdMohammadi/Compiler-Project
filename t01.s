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
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 16
jal L4
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 20
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
beq $t0, 0, L6
la $a0, TRUE
j L7
L6 :
la $a0, FALSE
L7 :
li $v0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 8
li $v0, 10
syscall
L4 :
sub $sp, $sp, 0
sub $t0, $fp, 4
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 8
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
and $t0, $t0, $t1
add $sp, $fp, 4
move $v0, $t0
jr $ra
add $sp, $sp, 0
lw  $ra, -16($fp)
lw  $fp, -12($fp)
jr $ra
L0 :
lw $t0, 4($sp)
beq $t0, 0, L8
li $t0, 1
j L9
L8:
li $t0, 0
L9:
move $v0, $t0
lw  $ra, 8($fp)
lw  $fp, 12($fp)
jr $ra
L1 :
lw $t0, 4($fp)
move $v0, $t0
lw  $ra, 8($fp)
lw  $fp, 12($fp)
jr $ra
L2 :
l.s $f0, 4($fp)
cvt.w.s $f0, $f0
mfc1 $t0, $f0
move $v0, $t0
lw  $ra, 8($fp)
lw  $fp, 12($fp)
jr $ra
L3 :
lw $t0, 4($fp)
mtc1 $t0, $f0
cvt.s.w $f0, $f0
lw  $ra, 8($fp)
lw  $fp, 12($fp)
jr $ra
