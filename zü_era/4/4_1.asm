bargraph:
dw

call init_bargraph
call step_bargraph2

jmp programend

init_bargraph:
mov [bargraph], 0
ret

step_bargraph1:
mov ax, 1
begin:
ror ax, 1
push ax
and ax, [bargraph]
cmp ax, 0
pop ax
jne begin
end:
or [bargraph], ax
ret

step_bargraph2:
cmp [bargraph], 0
jne cont
mov ax, 1
ror ax, 1
or [bargraph], ax
ret
cont:
mov ax, [bargraph]
ror ax, 1
mov [bargraph], ax
ret

programend:
