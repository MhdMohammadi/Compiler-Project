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
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L1:
li $t0, 1
beq $t0, 0, L2
sub $sp, $sp, 0
li $v0, 9
li $a0, 23
syscall
move $t0, $v0
li $t1, 18
sw $t1, 0($t0)
li $t2, 80
sb $t2 4($t0)
li $t2, 108
sb $t2 5($t0)
li $t2, 101
sb $t2 6($t0)
li $t2, 97
sb $t2 7($t0)
li $t2, 115
sb $t2 8($t0)
li $t2, 101
sb $t2 9($t0)
li $t2, 32
sb $t2 10($t0)
li $t2, 101
sb $t2 11($t0)
li $t2, 110
sb $t2 12($t0)
li $t2, 116
sb $t2 13($t0)
li $t2, 101
sb $t2 14($t0)
li $t2, 114
sb $t2 15($t0)
li $t2, 32
sb $t2 16($t0)
li $t2, 116
sb $t2 17($t0)
li $t2, 104
sb $t2 18($t0)
li $t2, 101
sb $t2 19($t0)
li $t2, 32
sb $t2 20($t0)
li $t2, 35
sb $t2 21($t0)
li $t2, 0
sb $t2, 22($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
sub $t0, $fp, 20
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
li $v0, 9
li $a0, 13
syscall
move $t0, $v0
li $t1, 8
sw $t1, 0($t0)
li $t2, 32
sb $t2 4($t0)
li $t2, 110
sb $t2 5($t0)
li $t2, 117
sb $t2 6($t0)
li $t2, 109
sb $t2 7($t0)
li $t2, 98
sb $t2 8($t0)
li $t2, 101
sb $t2 9($t0)
li $t2, 114
sb $t2 10($t0)
li $t2, 58
sb $t2 11($t0)
li $t2, 0
sb $t2, 12($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 5
syscall
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
slt $t0, $t1, $t0
beq $t0, 0, L5
j L2
j L4
L5:
L4:
sub $t0, $fp, 16
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
add $t0, $t1, $t0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 0
L3:
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 20
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
li $v0, 9
li $a0, 12
syscall
move $t0, $v0
li $t1, 7
sw $t1, 0($t0)
li $t2, 83
sb $t2 4($t0)
li $t2, 117
sb $t2 5($t0)
li $t2, 109
sb $t2 6($t0)
li $t2, 32
sb $t2 7($t0)
li $t2, 111
sb $t2 8($t0)
li $t2, 102
sb $t2 9($t0)
li $t2, 32
sb $t2 10($t0)
li $t2, 0
sb $t2, 11($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
sub $t0, $fp, 20
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
li $v0, 9
li $a0, 16
syscall
move $t0, $v0
li $t1, 11
sw $t1, 0($t0)
li $t2, 32
sb $t2 4($t0)
li $t2, 105
sb $t2 5($t0)
li $t2, 116
sb $t2 6($t0)
li $t2, 101
sb $t2 7($t0)
li $t2, 109
sb $t2 8($t0)
li $t2, 115
sb $t2 9($t0)
li $t2, 32
sb $t2 10($t0)
li $t2, 105
sb $t2 11($t0)
li $t2, 115
sb $t2 12($t0)
li $t2, 58
sb $t2 13($t0)
li $t2, 32
sb $t2 14($t0)
li $t2, 0
sb $t2, 15($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
sub $t0, $fp, 16
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
add $sp, $sp, 12
li $v0, 10
syscall
