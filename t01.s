.text
.globl main
main:
la $a0, errorMsg
addi $v0, $zero, 4
syscall
jr $ra
.data
errorMsg: .assciiz "SemanticError"
