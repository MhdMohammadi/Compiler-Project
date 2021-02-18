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
sub $sp, $sp, 4
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
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
seq $t0, $t1, $t0
beq $t0, 0, L2
li $v0, 9
li $a0, 7
syscall
move $t0, $v0
li $t1, 2
sw $t1, 0($t0)
li $t2, 104
sb $t2 4($t0)
li $t2, 105
sb $t2 5($t0)
li $t2, 0
sb $t2, 6($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
j L1
L2:
L1:
add $sp, $sp, 4
li $v0, 10
syscall
