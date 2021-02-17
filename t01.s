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
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L5:
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 10
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L6
sub $sp, $sp, 0
sub $t0, $fp, 12
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 0
L7:
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t1, $t0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
j L5
L6:
add $sp, $sp, 4
li $v0, 10
syscall
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
