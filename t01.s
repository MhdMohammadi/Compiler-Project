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
li $t0, 10
add $t0, $t0, 1
move $a0, $t0
mul $a0, $a0, 4
li $v0, 9
syscall
sub $t0, $t0, 1
sw $t0, 0($v0)
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 10
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 10
add $t0, $t0, 1
move $a0, $t0
mul $a0, $a0, 4
li $v0, 9
syscall
sub $t0, $t0, 1
sw $t0, 0($v0)
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 20
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
lw $t2, 0($t0)
lw $t3, 0($t1)
add $t2, $t2, $t3
add $t2, $t2, 1
move $a0, $t2
mul $a0, $a0, 4
li $v0, 9
syscall
move $t4, $v0
move $t5, $t4
sub $t2, $t2, 1
sw $t2, 0($t4)
lw $t2, 0($t0)
add $t4, $t4, 4
add $t1, $t1, 4
add $t0, $t0, 4
L1:
beq $t3, 0, L2
lw $t6, 0($t1)
sw $t6, 0($t4)
add $t4, $t4, 4
add $t1, $t1, 4
sub $t3, $t3, 1
j L1
L2:
beq $t2, 0, L3
lw $t6, 0($t0)
sw $t6, 0($t4)
add $t4, $t4, 4
add $t0, $t0, 4
sub $t2, $t2, 1
j L2
L3:
move $t0, $t5
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 10
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
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
