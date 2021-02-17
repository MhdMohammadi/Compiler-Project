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
li $t0, 1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
xor $t0, $t0, 1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
or $t0, $t0, $t1
lw $t1, 0($sp)
add $sp, $sp, 4
and $t0, $t0, $t1
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
beq $t0, 0, L1
la $a0, TRUE
j L2
L1 :
la $a0, FALSE
L2 :
li $v0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
sub $t0, $fp, 16
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
