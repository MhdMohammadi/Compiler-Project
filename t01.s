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
li $v0, 9
li $a0, 23
syscall
li $t0, $v0
li $t1, 18
sw $t1, 0($t0)
li $t2, 34
sb $t2 4($t0)
li $t2, 109
sb $t2 5($t0)
li $t2, 105
sb $t2 6($t0)
li $t2, 115
sb $t2 7($t0)
li $t2, 104
sb $t2 8($t0)
li $t2, 101
sb $t2 9($t0)
li $t2, 32
sb $t2 10($t0)
li $t2, 107
sb $t2 11($t0)
li $t2, 104
sb $t2 12($t0)
li $t2, 111
sb $t2 13($t0)
li $t2, 111
sb $t2 14($t0)
li $t2, 98
sb $t2 15($t0)
li $t2, 32
sb $t2 16($t0)
li $t2, 115
sb $t2 17($t0)
li $t2, 104
sb $t2 18($t0)
li $t2, 101
sb $t2 19($t0)
li $t2, 63
sb $t2 20($t0)
li $t2, 34
sb $t2 21($t0)
li $t2, 0
sb $t2, 22($t0)
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
add $sp, $sp, 4
li $v0, 10
syscall
