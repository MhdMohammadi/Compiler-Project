.data
TRUE: .asciiz "true"
FALSE: .asciiz "false"
ENDL: .asciiz "\n"
buffer: .space 1000
.text
.globl main
main :
move $fp, $sp
sub $sp, $sp, 8
sub $sp, $sp, 12
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 8
la $a0, buffer
li $a1, 1000
syscall
li $t0, 0
L2 :
lb $t1, 0($a0)
beq $t1, 10, L1
add $t0, $t0, 1
add $a0, $a0, 1
j L2
L1 :
sb $zero, 0($a0)
add $a0, $t0, 5
li $v0, 9
syscall
move $t1, $v0
la $a0, buffer
sw $t0, 0($t1)
add $t1, $t1, 4
L3 :
beq $t0, $zero, L4
lb $t2, 0($a0)
sb $t2, 0($t1)
sb $zero, 0($a0)
add $a0, $a0, 1
add $t1, $t1, 1
sub $t0, $t0, 1
j L3
L4 :
sb $zero, 0($t1)
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
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
