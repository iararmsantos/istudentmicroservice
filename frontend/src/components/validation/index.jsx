
export const required = (value) => value ? undefined : 'Required field';

export const validPhone = (value) => {
    const phoneRegExp = new RegExp(/^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im);
    return phoneRegExp.test(value) ? undefined : 'Type a valid phone number';
}

export const validEmail = (value) => {
    const emailRegExp = new RegExp(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/);
    return emailRegExp.test(value) ? undefined : 'Type a valid email';
}
