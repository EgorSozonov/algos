#define RAD2DEG(x)          ((x)/PI*180)
#define DEG2RAD(x)          ((x)*PI/180)
#define ABS(x) (((x) <  0) ? -(x) : (x))
#define DIFF(a,b) ABS((a)-(b))
#define MIN(a, b) (((a) < (b)) ? (a) : (b))
#define MAX(a, b) (((a) > (b)) ? (a) : (b))
#define CLAMP(a, x, b) (((x) < (a)) ? (a) : \
            ((b) < (x)) ? (b) : (x))
#define CLAMPTOP(a, b) MIN(a, b)
#define CLAMPBOT(a, b) MAX(a, b)
#define BIT(x)          (1<<(x))
#define SETBIT(x,p)     ((x)|(1<<(p)))
#define CLEARBIT(x,p)   ((x)&(~(1<<(p))))
#define GETBIT(x,p)     (((x)>>(p))&1)
#define TOGGLEBIT(x,p)  ((x)^(1<<(p)))

#define IMPLIES(x, y) (!(x) || (y))

#define MEMBER(T, m) (((T*) 0)->m)
#define OFFSETOFMEMBER(T, m) INTFROMPTR(&MEMBER(T, m))

#define INTFROMPTR(p) (unsigned long long)((char*)p - (char*)0)
#define PTRFROMINT(n) (void*)((char*)0 + (n))
