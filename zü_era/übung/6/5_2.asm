begin:
cmp byte [eax], 0
je end
cmp byte [eax], 0x61
jl notlower
cmp byte [eax], 0x7a
jg notlower
sub byte [eax], 32
notlower:
inc eax
jmp begin
end:
