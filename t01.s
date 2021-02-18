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
li $v0, 5
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
beq $t0, 0, null
li $t0, 1
j null
null:
li $t0, 0
null:
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
add $sp, $sp, 4
li $v0, 10
syscall
