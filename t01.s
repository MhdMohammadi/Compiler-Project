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
li $a0, 0
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
li $t0, 2
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
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 4
li $v0, 10
syscall
L0 :
sub $sp, $sp, 0
lw $t0, 0($fp)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
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
sub $sp, $fp, 12
move $v0, $t0
jr $ra
add $sp, $sp, 0
sub $sp, $fp, 3
jr $ra
L1 :
sub $sp, $sp, 0
sub $t0, $fp, 4
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 2
lw $t1, 0($sp)
add $sp, $sp, 4
mul $t0, $t1, $t0
sub $sp, $fp, 12
move $v0, $t0
jr $ra
add $sp, $sp, 0
sub $sp, $fp, 3
jr $ra
