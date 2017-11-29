console:
resb 40

print_hex_digit:
push bx
push ax
mov ax, 9
mov bx, 0xF
and ax, bx
cmp ax, 9
jg alpha
add ax, 48
jmp write_output
alpha:
add ax, 55
write_output:
mov [console], ax

pop ax
pop bx
