; Befehl zum Erhalten der Einträge in der Tabelle?
; Hätte Zeilennummern genommen...
; Für richtige
; Zeilennummern.

sprungtabelle:
dd waschprogramm1
dd waschprogramm2
dd waschprogramm3
dd waschprogramm4
dd waschprogramm5
dd waschprogramm6
dd waschprogramm7
dd waschprogramm8

jmp anfang

waschprogramm1:

mov edx, 1
jmp ende

waschprogramm2:

mov edx, 2
jmp ende

waschprogramm3:

mov edx, 3
jmp ende

waschprogramm4:

mov edx, 4
jmp ende

waschprogramm5:

mov edx, 5
jmp ende

waschprogramm6:

mov edx, 6
jmp ende

waschprogramm7:

mov edx, 7
jmp ende

waschprogramm8:

mov edx, 8
jmp ende

error:

mov edx, 0
jmp ende

anfang:

cmp eax, 9
jg error

cmp eax, 1
jl error

xor ebx, ebx

dec eax
mov ecx, sprungtabelle
mov ebx, [ecx+eax*4]
jmp ebx

ende:
