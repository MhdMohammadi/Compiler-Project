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
sub $sp, $sp, 16
li $v0, 9
li $a0, 72
syscall
move $t0, $v0
li $t1, 67
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
li $t2, 110
sb $t2 21($t0)
li $t2, 117
sb $t2 22($t0)
li $t2, 109
sb $t2 23($t0)
li $t2, 98
sb $t2 24($t0)
li $t2, 101
sb $t2 25($t0)
li $t2, 114
sb $t2 26($t0)
li $t2, 115
sb $t2 27($t0)
li $t2, 32
sb $t2 28($t0)
li $t2, 40
sb $t2 29($t0)
li $t2, 109
sb $t2 30($t0)
li $t2, 97
sb $t2 31($t0)
li $t2, 120
sb $t2 32($t0)
li $t2, 32
sb $t2 33($t0)
li $t2, 99
sb $t2 34($t0)
li $t2, 111
sb $t2 35($t0)
li $t2, 117
sb $t2 36($t0)
li $t2, 110
sb $t2 37($t0)
li $t2, 116
sb $t2 38($t0)
li $t2, 58
sb $t2 39($t0)
li $t2, 32
sb $t2 40($t0)
li $t2, 49
sb $t2 41($t0)
li $t2, 48
sb $t2 42($t0)
li $t2, 48
sb $t2 43($t0)
li $t2, 44
sb $t2 44($t0)
li $t2, 32
sb $t2 45($t0)
li $t2, 101
sb $t2 46($t0)
li $t2, 110
sb $t2 47($t0)
li $t2, 116
sb $t2 48($t0)
li $t2, 101
sb $t2 49($t0)
li $t2, 114
sb $t2 50($t0)
li $t2, 32
sb $t2 51($t0)
li $t2, 45
sb $t2 52($t0)
li $t2, 49
sb $t2 53($t0)
li $t2, 32
sb $t2 54($t0)
li $t2, 116
sb $t2 55($t0)
li $t2, 111
sb $t2 56($t0)
li $t2, 32
sb $t2 57($t0)
li $t2, 101
sb $t2 58($t0)
li $t2, 110
sb $t2 59($t0)
li $t2, 100
sb $t2 60($t0)
li $t2, 32
sb $t2 61($t0)
li $t2, 115
sb $t2 62($t0)
li $t2, 111
sb $t2 63($t0)
li $t2, 111
sb $t2 64($t0)
li $t2, 110
sb $t2 65($t0)
li $t2, 101
sb $t2 66($t0)
li $t2, 114
sb $t2 67($t0)
li $t2, 41
sb $t2 68($t0)
li $t2, 58
sb $t2 69($t0)
li $t2, 32
sb $t2 70($t0)
li $t2, 0
sb $t2, 71($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 100
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
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L10:
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 100
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L11
sub $sp, $sp, 4
sub $t0, $fp, 28
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 5
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 28
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
sub $t0, $zero, $t0
lw $t1, 0($sp)
add $sp, $sp, 4
seq $t0, $t1, $t0
beq $t0, 0, L14
j L11
j L13
L14:
L13:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 28
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 4
L12:
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
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
j L10
L11:
sub $t0, $fp, 24
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
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
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L15:
sub $t0, $fp, 16
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L16
sub $sp, $sp, 0
sub $t0, $fp, 24
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 0
L17:
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
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
j L15
L16:
lw $t0, 0($fp)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 24
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
li $v0, 9
li $a0, 17
syscall
move $t0, $v0
li $t1, 12
sw $t1, 0($t0)
li $t2, 65
sb $t2 4($t0)
li $t2, 102
sb $t2 5($t0)
li $t2, 116
sb $t2 6($t0)
li $t2, 101
sb $t2 7($t0)
li $t2, 114
sb $t2 8($t0)
li $t2, 32
sb $t2 9($t0)
li $t2, 115
sb $t2 10($t0)
li $t2, 111
sb $t2 11($t0)
li $t2, 114
sb $t2 12($t0)
li $t2, 116
sb $t2 13($t0)
li $t2, 58
sb $t2 14($t0)
li $t2, 32
sb $t2 15($t0)
li $t2, 0
sb $t2, 16($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L18:
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 24
lw $t0, 0($t0)
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L19
sub $sp, $sp, 0
sub $t0, $fp, 24
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 0
L20:
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
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
j L18
L19:
add $sp, $sp, 16
li $v0, 10
syscall
L0 :
sub $sp, $sp, 12
sub $t0, $fp, 24
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L2:
sub $t0, $fp, 16
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 24
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sub $t0, $t1, $t0
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L3
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L5:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 24
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sub $t0, $t1, $t0
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sub $t0, $t1, $t0
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L6
sub $t0, $fp, 4
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 20
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
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
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t0, $t1
beq $t0, 0, L9
sub $sp, $sp, 4
sub $t0, $fp, 28
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 20
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 4
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 20
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
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
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 4
lw $t0, 0($t0)
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
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 28
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 4
j L8
L9:
L8:
L7:
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
j L5
L6:
L4:
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
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
j L2
L3:
add $sp, $sp, 12
sub $sp, $fp, 12
jr $ra
