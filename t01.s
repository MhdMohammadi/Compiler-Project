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
li $t0, 2
lw $t1, 0($sp)
add $sp, $sp, 4
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 10
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 3
lw $t1, 0($sp)
add $sp, $sp, 4
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 12
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 2
lw $t1, 0($sp)
add $sp, $sp, 4
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 3
lw $t1, 0($sp)
add $sp, $sp, 4
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sub $t0, $t1, $t0
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 4
li $v0, 10
syscall
