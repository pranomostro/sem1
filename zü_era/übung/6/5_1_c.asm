console:
resb 40

print_16:
push eax
push ebx
push ecx
push edx

mov ax, 1234
mov bx, 10
mov ecx, 0

begin:
cmp ax, 0
je end
mov dx, 0
div bx
add dx, 48
push dx
inc ecx
je end
jmp begin
end:

mov edx, 0

begin2:
cmp edx, ecx
je end2
pop ax
mov [edx], ax
inc edx
jmp begin2
end2:

pop edx
pop ecx
pop ebx
pop eax
