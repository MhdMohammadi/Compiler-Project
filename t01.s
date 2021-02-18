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
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L1:
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 10
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L2
sub $sp, $sp, 0
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L4:
sub $t0, $fp, 16
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 4
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L5
sub $sp, $sp, 0
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t1, $t0
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 2
lw $t1, 0($sp)
add $sp, $sp, 4
div $t1, $t0
mfhi $t0
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
seq $t0, $t1, $t0
beq $t0, 0, L8
sub $sp, $sp, 0
j L6
add $sp, $sp, 0
j L7
L8:
sub $sp, $sp, 0
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t1, $t0
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 0
L7:
add $sp, $sp, 0
L6:
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
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
j L4
L5:
add $sp, $sp, 0
L3:
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
j L1
L2:
add $sp, $sp, 8
li $v0, 10
syscall
