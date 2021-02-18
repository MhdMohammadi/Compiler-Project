.data
adj:    .word   0
rev:    .word   0
mark:    .word   0
topol:    .word   0
TRUE: .asciiz "true"
FALSE: .asciiz "false"
ENDL: .asciiz "\n"
buffer: .space 1000
.text
.globl main
main :
move $fp, $sp
sub $sp, $sp, 8
sub $sp, $sp, 24
la $t0, topol
sub $sp, $sp, 4
sw $t0, 0($sp)
li $a0, 16
li $v0, 9
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
la $t0, topol
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 8
jal L0
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 12
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 5
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 5
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
la $t0, adj
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
la $t0, rev
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
la $t0, mark
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
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L30:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L31
la $t0, mark
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
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L32:
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
j L30
L31:
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L33:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L34
sub $sp, $sp, 0
la $t0, adj
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
li $a0, 16
li $v0, 9
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
la $t0, rev
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
li $a0, 16
li $v0, 9
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
la $t0, adj
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
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 8
jal L0
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 12
la $t0, rev
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
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 8
jal L0
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 12
add $sp, $sp, 0
L35:
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
j L33
L34:
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L36:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L37
sub $sp, $sp, 8
sub $t0, $fp, 36
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 5
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 40
sub $sp, $sp, 4
sw $t0, 0($sp)
li $v0, 5
syscall
move $t0, $v0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
la $t0, adj
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 36
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 40
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
la $t0, rev
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 40
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 36
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
add $sp, $sp, 8
L38:
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
j L36
L37:
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L39:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L40
sub $sp, $sp, 0
la $t0, mark
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
xor $t0, $t0, 1
beq $t0, 0, L43
sub $sp, $sp, 0
lw $t0, 0($fp)
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
jal L5
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 16
add $sp, $sp, 0
j L42
L43:
L42:
add $sp, $sp, 0
L41:
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
j L39
L40:
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L44:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L45
la $t0, mark
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
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L46:
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
j L44
L45:
sub $t0, $fp, 32
sub $sp, $sp, 4
sw $t0, 0($sp)
la $t0, topol
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 8
jal L4
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 12
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 28
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
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L47:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L48
sub $sp, $sp, 0
sub $t0, $fp, 28
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
sub $t0, $fp, 32
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 20
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
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 28
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
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 0
L49:
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
j L47
L48:
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L50:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 12
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L51
sub $sp, $sp, 4
sub $t0, $fp, 36
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 28
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
la $t0, mark
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 36
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
xor $t0, $t0, 1
beq $t0, 0, L54
sub $sp, $sp, 0
li $v0, 9
li $a0, 22
syscall
move $t0, $v0
li $t1, 17
sw $t1, 0($t0)
li $t2, 78
sb $t2 4($t0)
li $t2, 101
sb $t2 5($t0)
li $t2, 119
sb $t2 6($t0)
li $t2, 32
sb $t2 7($t0)
li $t2, 83
sb $t2 8($t0)
li $t2, 67
sb $t2 9($t0)
li $t2, 67
sb $t2 10($t0)
li $t2, 32
sb $t2 11($t0)
li $t2, 99
sb $t2 12($t0)
li $t2, 111
sb $t2 13($t0)
li $t2, 109
sb $t2 14($t0)
li $t2, 112
sb $t2 15($t0)
li $t2, 111
sb $t2 16($t0)
li $t2, 110
sb $t2 17($t0)
li $t2, 101
sb $t2 18($t0)
li $t2, 110
sb $t2 19($t0)
li $t2, 116
sb $t2 20($t0)
li $t2, 0
sb $t2, 21($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
lw $t0, 0($fp)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 36
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 12
jal L6
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 16
li $v0, 9
li $a0, 19
syscall
move $t0, $v0
li $t1, 14
sw $t1, 0($t0)
li $t2, 45
sb $t2 4($t0)
li $t2, 45
sb $t2 5($t0)
li $t2, 45
sb $t2 6($t0)
li $t2, 45
sb $t2 7($t0)
li $t2, 45
sb $t2 8($t0)
li $t2, 45
sb $t2 9($t0)
li $t2, 45
sb $t2 10($t0)
li $t2, 45
sb $t2 11($t0)
li $t2, 45
sb $t2 12($t0)
li $t2, 45
sb $t2 13($t0)
li $t2, 45
sb $t2 14($t0)
li $t2, 45
sb $t2 15($t0)
li $t2, 45
sb $t2 16($t0)
li $t2, 45
sb $t2 17($t0)
li $t2, 0
sb $t2, 18($t0)
li $v0, 4
move $a0, $t0
add $a0, $a0, 4
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 0
j L53
L54:
L53:
add $sp, $sp, 4
L52:
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
j L50
L51:
add $sp, $sp, 24
li $v0, 10
syscall
L5 :
sub $sp, $sp, 8
la $t0, mark
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
la $t0, adj
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 8
jal L4
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 12
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L20:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L21
sub $sp, $sp, 4
sub $t0, $fp, 24
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
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
la $t0, mark
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 24
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
xor $t0, $t0, 1
beq $t0, 0, L24
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
jal L5
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 16
j L23
L24:
L23:
add $sp, $sp, 4
L22:
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
j L20
L21:
la $t0, topol
lw $t0, 0($t0)
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
add $sp, $sp, 8
sub $sp, $fp, 12
jr $ra
L6 :
sub $sp, $sp, 8
la $t0, mark
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
la $t0, rev
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $sp, $sp, 8
sw $fp, 4($sp)
sw $ra, 0($sp)
add $fp, $sp, 8
jal L4
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 12
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L25:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L26
sub $sp, $sp, 4
sub $t0, $fp, 24
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
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
la $t0, mark
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 24
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
xor $t0, $t0, 1
beq $t0, 0, L29
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
jal L6
move $t0, $v0
lw $ra, 0($sp)
lw $fp, 4($sp)
add $sp, $sp, 16
j L28
L29:
L28:
add $sp, $sp, 4
L27:
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
j L25
L26:
sub $t0, $fp, 4
lw $t0, 0($t0)
li $v0, 1
move $a0, $t0
syscall
la $a0, ENDL
li $v0, 4
syscall
add $sp, $sp, 8
sub $sp, $fp, 12
jr $ra
L0 :
sub $sp, $sp, 0
lw $t0, 0($fp)
add $t0, $t0, 4
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
lw $t0, 0($fp)
add $t0, $t0, 0
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
lw $t0, 0($fp)
add $t0, $t0, 8
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
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
add $sp, $sp, 0
sub $sp, $fp, 8
jr $ra
L1 :
sub $sp, $sp, 0
lw $t0, 0($fp)
add $t0, $t0, 4
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 0
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
seq $t0, $t1, $t0
beq $t0, 0, L12
sub $sp, $sp, 8
sub $t0, $fp, 16
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 2
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
mul $t0, $t1, $t0
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
sub $t0, $fp, 20
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
L8:
sub $t0, $fp, 20
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L9
sub $sp, $sp, 0
sub $t0, $fp, 16
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
lw $t0, 0($fp)
add $t0, $t0, 8
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
add $sp, $sp, 0
L10:
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
j L8
L9:
lw $t0, 0($fp)
add $t0, $t0, 8
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 16
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
lw $t0, 0($fp)
add $t0, $t0, 4
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 2
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
mul $t0, $t1, $t0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 8
j L11
L12:
L11:
lw $t0, 0($fp)
add $t0, $t0, 8
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 0
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
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
lw $t0, 0($fp)
add $t0, $t0, 0
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 0
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
add $sp, $sp, 0
sub $sp, $fp, 12
jr $ra
L2 :
sub $sp, $sp, 0
lw $t0, 0($fp)
add $t0, $t0, 0
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 0
lw $t1, 0($sp)
add $sp, $sp, 4
seq $t0, $t1, $t0
beq $t0, 0, L14
sub $sp, $fp, 8
jr $ra
j L13
L14:
L13:
lw $t0, 0($fp)
add $t0, $t0, 0
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 0
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
li $t0, 1
lw $t1, 0($sp)
add $sp, $sp, 4
sub $t0, $t1, $t0
lw $t1, 0($sp)
add $sp, $sp, 4
sw $t0, 0($t1)
add $sp, $sp, 0
sub $sp, $fp, 8
jr $ra
L3 :
sub $sp, $sp, 0
sub $t0, $fp, 4
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 0
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
xor $t0, $t0, 1
beq $t0, 0, L16
li $t0, 1
sub $t0, $zero, $t0
sub $sp, $fp, 12
move $v0, $t0
jr $ra
j L15
L16:
L15:
lw $t0, 0($fp)
add $t0, $t0, 8
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
sub $t0, $fp, 4
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
add $t0, $t0, 1
mul $t0, $t0, 4
add $t0, $t0, $t1
lw $t0, 0($t0)
sub $sp, $fp, 12
move $v0, $t0
jr $ra
add $sp, $sp, 0
sub $sp, $fp, 12
jr $ra
L4 :
sub $sp, $sp, 8
sub $t0, $fp, 12
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 0
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
L17:
sub $t0, $fp, 16
lw $t0, 0($t0)
sub $sp, $sp, 4
sw $t0, 0($sp)
lw $t0, 0($fp)
add $t0, $t0, 0
lw $t0, 0($t0)
lw $t1, 0($sp)
add $sp, $sp, 4
slt $t0, $t1, $t0
beq $t0, 0, L18
sub $t0, $fp, 12
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
lw $t0, 0($fp)
add $t0, $t0, 8
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
L19:
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
j L17
L18:
sub $t0, $fp, 12
lw $t0, 0($t0)
sub $sp, $fp, 8
move $v0, $t0
jr $ra
add $sp, $sp, 8
sub $sp, $fp, 8
jr $ra
