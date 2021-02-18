.data
a:    .word   0
TRUE: .asciiz "true"
FALSE: .asciiz "false"
ENDL: .asciiz "\n"
.text
.globl main
main :
move $fp, $sp
sub $sp, $sp, 8
sub $sp, $sp, 0
la $t0, a
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 20
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
la $t0, a
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 0
li $v0, 10
syscall
