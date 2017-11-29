print_hex:
push eax
push ebx
push ecx
push edx

mov ecx, 0

begin:
cmp ecx, 8
je end
rol eax, 4
mov ebx, 0xF
and ebx, eax
cmp ebx, 9
jg alpha
add ebx, 48
jmp added
alpha:
add ebx, 55
added:
mov [ecx], ebx
inc ecx
jmp begin
end:
pop ecx
pop ebx
pop eax
