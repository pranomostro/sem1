; Der Nachteil dieses Vorgehens ist
; die Tatsache, dass ziemlich viele
; repetitive Vergleiche gemacht werden
; müssen, bei denen sich schnell ein
; kleiner Fehler einschleichen kann.

cmp eax, 1
je waschprogramm1

cmp eax, 2
je waschprogramm2

cmp eax, 3
je waschprogramm3

cmp eax, 4
je waschprogramm4

cmp eax, 5
je waschprogramm5

cmp eax, 6
je waschprogramm6

cmp eax, 7
je waschprogramm7

cmp eax, 8
je waschprogramm8

jmp error

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

ende:
