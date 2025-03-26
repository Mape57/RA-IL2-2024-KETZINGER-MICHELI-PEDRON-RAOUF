export function getSportsAge(birthDate) {
	if (!birthDate) return null;

	const parts = birthDate.split('-');
	if (parts.length !== 3) return null;

	const birthYear = parseInt(parts[0]);
	const birthMonth = parseInt(parts[1]);

	const now = new Date();
	const currentYear = now.getFullYear();
	const currentMonth = now.getMonth() + 1;

	let age = currentYear - birthYear;

	if (currentMonth < 9) age--;
	if (birthMonth < 9) age++;

	return age;
}

export function getDay(dayWeek) {
	if (!dayWeek) return null;
	const days = ['lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi', 'dimanche'];
	return days[dayWeek - 1];
}

export function getFormattedScore(score) {
	const parts = score.replaceAll('-', '').split('/');

	const hard = parseInt(parts[0].replace('hard', ''));
	const soft = parseInt(parts[1].replace('soft', ''));

	let formattedScore = '';
	if (hard !== 0) {
		formattedScore += hard + ' difficile' + (hard > 1 ? 's' : '');
	}

	if (soft !== 0) {
		if (formattedScore) formattedScore += ' et ';
		formattedScore += soft + ' facile' + (soft > 1 ? 's' : '');
	}

	return formattedScore;
}