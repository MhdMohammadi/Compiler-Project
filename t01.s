.data
TRUE: .asciiz "true"
FALSE: .asciiz "false"
ENDL: .asciiz "\n"
.text
.globl main
main :
move $fp, $sp
sub $sp, $sp, 8
sub $sp, $sp, 12
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 9
li $a0, 12
syscall
move $t0, $v0
li $t1, 7
sw $t1, 0($t0)
li $t2, 99
sb $t2 4($t0)
li $t2, 97
sb $t2 5($t0)
li $t2, 108
sb $t2 6($t0)
li $t2, 108
sb $t2 7($t0)
li $t2, 32
sb $t2 8($t0)
li $t2, 109
sb $t2 9($t0)
li $t2, 101
sb $t2 10($t0)
li $t2, 0
sb $t2, 11($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 9
li $a0, 13
syscall
move $t0, $v0
li $t1, 8
sw $t1, 0($t0)
li $t2, 109
sb $t2 4($t0)
li $t2, 97
sb $t2 5($t0)
li $t2, 109
sb $t2 6($t0)
li $t2, 97
sb $t2 7($t0)
li $t2, 99
sb $t2 8($t0)
li $t2, 105
sb $t2 9($t0)
li $t2, 116
sb $t2 10($t0)
li $t2, 97
sb $t2 11($t0)
li $t2, 0
sb $t2, 12($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 9
li $a0, 6
syscall
move $t0, $v0
li $t1, 1
sw $t1, 0($t0)
li $t2, 32
sb $t2 4($t0)
li $t2, 0
sb $t2, 5($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
lw $t2, 0($t0)
lw $t3, 0($t1)
add $t2, $t2, $t3
add $t2, $t2, 1
move $a0, $t2
add $a0, $a0, 4
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
lb $t6, 0($t1)
sb $t6, 0($t4)
add $t4, $t4, 1
add $t1, $t1, 1
sub $t3, $t3, 1
j L1
L2:
beq $t2, 0, L3
lb $t6, 0($t0)
sb $t6, 0($t4)
add $t4, $t4, 1
add $t0, $t0, 1
sub $t2, $t2, 1
j L2
L3:
sb $zero, 0($t4)
move $t0, $t5
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
lw $t2, 0($t0)
lw $t3, 0($t1)
add $t2, $t2, $t3
add $t2, $t2, 1
move $a0, $t2
add $a0, $a0, 4
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
L4:
beq $t3, 0, L5
lb $t6, 0($t1)
sb $t6, 0($t4)
add $t4, $t4, 1
add $t1, $t1, 1
sub $t3, $t3, 1
j L4
L5:
beq $t2, 0, L6
lb $t6, 0($t0)
sb $t6, 0($t4)
add $t4, $t4, 1
add $t0, $t0, 1
sub $t2, $t2, 1
j L5
L6:
sb $zero, 0($t4)
move $t0, $t5
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
sub $t0, $fp, 16
lw $t0, 0($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
sub $t0, $fp, 20
lw $t0, 0($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 12
li $v0, 10
syscall
