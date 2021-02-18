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
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 8
la $a0, buffer
li $a1, 1000
syscall
li $t0, 0
L5 :
lb $t1, 0($a0)
beq $t1, 10, L4
add $t0, $t0, 1
add $a0, $a0, 1
j L5
L4 :
sb $zero, 0($a0)
add $a0, $t0, 5
li $v0, 9
syscall
move $t1, $v0
la $a0, buffer
sw $t0, 0($t1)
add $t1, $t1, 4
L6 :
beq $t0, $zero, L7
lb $t2, 0($a0)
sb $t2, 0($t1)
sb $zero, 0($a0)
add $a0, $a0, 1
add $t1, $t1, 1
sub $t0, $t0, 1
j L6
L7 :
sb $zero, 0($t1)
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 5
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li $a0, 12
li $v0, 9
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
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
add $fp, $sp, 12
jal L0
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 16
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 12
jal L1
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 16
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 8
jal L2
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 12
add $sp, $sp, 12
li $v0, 10
syscall
L0 :
sub $sp, $sp, 0
lw $t0, 0($fp)
add $t0, $t0, 0
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 0
sub $sp, $fp, 3
jr $ra
L1 :
sub $sp, $sp, 0
lw $t0, 0($fp)
add $t0, $t0, 4
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 0
sub $sp, $fp, 3
jr $ra
L2 :
sub $sp, $sp, 0
li $v0, 9
li $a0, 11
syscall
move $t0, $v0
li $t1, 6
sw $t1, 0($t0)
li $t2, 78
sb $t2 4($t0)
li $t2, 97
sb $t2 5($t0)
li $t2, 109
sb $t2 6($t0)
li $t2, 101
sb $t2 7($t0)
li $t2, 58
sb $t2 8($t0)
li $t2, 32
sb $t2 9($t0)
li $t2, 0
sb $t2, 10($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
lw $t0, 0($fp)
add $t0, $t0, 0
lw $t0, 0($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
li $v0, 9
li $a0, 11
syscall
move $t0, $v0
li $t1, 6
sw $t1, 0($t0)
li $t2, 32
sb $t2 4($t0)
li $t2, 65
sb $t2 5($t0)
li $t2, 103
sb $t2 6($t0)
li $t2, 101
sb $t2 7($t0)
li $t2, 58
sb $t2 8($t0)
li $t2, 32
sb $t2 9($t0)
li $t2, 0
sb $t2, 10($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
lw $t0, 0($fp)
add $t0, $t0, 4
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 0
sub $sp, $fp, 2
jr $ra
